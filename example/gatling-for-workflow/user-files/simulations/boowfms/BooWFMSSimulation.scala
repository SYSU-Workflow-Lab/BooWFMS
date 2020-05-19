package boowfms

import java.util.UUID

import scala.io.Source

class BooWFMSSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:10234")
    .headers(Map("Content-Type" -> "application/x-www-form-urlencoded"))

  val csSource = Source.fromFile("..\\user-files\\simulations\\boowfms\\Crowdsourcing.xml", "UTF-8")
  val csContent = csSource.mkString
  csSource.close()

  val rSource = Source.fromFile("..\\user-files\\simulations\\boowfms\\Request.xml", "UTF-8")
  val rContent = rSource.mkString
  rSource.close()

  val client = new HttpClient
  System.setProperty("logging.logger.org.apache.http.wire", "ERROR")

  // create process
  val createProcess = new PostMethod("http://localhost:10234/business-process-data/process/createProcess")
  createProcess.addParameter(new NameValuePair("creatorId", "account-1"))
  createProcess.addParameter(new NameValuePair("name", "CrowdSourcing"))
  createProcess.addParameter(new NameValuePair("mainbo", "Request"))
  client.executeMethod(createProcess)
  val pid = (parse(createProcess.getResponseBodyAsString) \ "data").values.toString
  println(pid)
  createProcess.releaseConnection()

  // upload Crowdsourcing BO
  val uploadCSBO = new PostMethod("http://localhost:10234/engine-feign/engine/uploadBO")
  uploadCSBO.addParameter(new NameValuePair("pid", pid))
  uploadCSBO.addParameter(new NameValuePair("name", "CrowdSourcing"))
  uploadCSBO.addParameter(new NameValuePair("content", csContent))
  client.executeMethod(uploadCSBO)
  uploadCSBO.releaseConnection()

  // upload Request BO
  val uploadRBO = new PostMethod("http://localhost:10234/engine-feign/engine/uploadBO")
  uploadRBO.addParameter(new NameValuePair("pid", pid))
  uploadRBO.addParameter(new NameValuePair("name", "Request"))
  uploadRBO.addParameter(new NameValuePair("content", rContent))
  client.executeMethod(uploadRBO)
  uploadRBO.releaseConnection()

  val scn = scenario("CrowdSourcing")
    // *************************** Log In ***************************
    //    .exec(http("publisher_login")
    //      .post("/auth/connect")
    //      .formParam("username", "admin@admin")
    //      .formParam("password", "admin")
    //      .check(jsonPath("$..data").saveAs("publisherToken")))
    // *************************** Request Init ***************************
    .exec(http("publisher_submitProcess")
      .post("/business-process-data/instance/create")
      .formParam("pid", pid)
      .formParam("from", "GATLING.TEST")
      .formParam("creatorId", "account-1")
      .formParam("bindingType", "0")
      .formParam("launchType", "0")
      .formParam("failureType", "0")
      .formParam("binding", "")
      .check(jsonPath("$..data").saveAs("processInstanceId")))
    .pause(10)
    .exec(http("publisher_uploadMapping")
      .post("/business-process-data/rolemap/register")
      .formParam("processInstanceId", "${processInstanceId}")
      .formParam("organizationId", "organization-2")
      .formParam("dataVersion", "version1")
      .formParam("map", "decomposer,capability-6;decomposeQuerier,agent-1;decomposeVoter,capability-7;solveVoter,capability-5;solutionQuerier,agent-1;judger,capability-8;merger,capability-2;solver,capability-4")
    )
    .pause(1)
    .exec(http("publisher_loadParticipant")
      .post("/business-process-data/rolemap/loadParticipant")
      .formParam("processInstanceId", "${processInstanceId}")
    )
    .pause(5)
    .exec(http("publisher_startProcess")
      .post("/engine-feign/engine/launchProcess")
      .formParam("processInstanceId", "${processInstanceId}")
      .formParam("accountId", "account-1")
    )
    // *************************** Submit Request ***************************
    .pause(5)
    .exec(http("publisher_submit")
      .post("/engine-feign/engine/callback")
      .formParam("processInstanceId", "${processInstanceId}")
      .formParam("on", "Complete")
      .formParam("id", "Request")
      .formParam("event", "submit")
      .formParam("payload", "{\"taskName\":\"" + UUID.randomUUID().toString() + "\",\"taskDescription\":\"" + System.currentTimeMillis() + "\",\"judgeCount\":1,\"decomposeCount\":1,\"decomposeVoteCount\":1,\"solveCount\":1,\"solveVoteCount\":1}")
    )
    // *************************** Judge Task Request ***************************
    .exec(session => {
      session.set("loop1", true)
    })
    .doWhile("${loop1}") {
      pause(10)
        .exec(http("get_wid")
          .post("/resource/workitem/getAll")
          .formParam("processInstanceId", "${processInstanceId}")
          .check(jsonPath("$..data[0].WorkItemId[0]").saveAs("wid1")))
        .exec(session => {
          val str = session("wid1").as[String]
          //          println(s"wid1: \n$str")
          if (str.length < 5) {
            session.set("loop1", true)
          } else {
            session.set("loop1", false)
          }
        })
    }
    .pause(1)
    .exec(http("judge_task_start")
      .post("/resource/workitem/start")
      .formParam("workItemId", "${wid1}")
      .formParam("workerId", "account-3"))
    .pause(10)
    .exec(http("judge_task_complete")
      .post("/resource/workitem/complete")
      .formParam("workItemId", "${wid1}")
      .formParam("workerId", "account-3")
      .formParam("payload", "{\"simple\":1}"))
    // *************************** Solve Task Request ***************************
    .exec(session => {
      session.set("loop2", true)
    })
    .doWhile("${loop2}") {
      pause(10)
        .exec(http("get_wid")
          .post("/resource/workitem/getAll")
          .formParam("processInstanceId", "${processInstanceId}")
          .check(jsonPath("$..data[0].WorkItemId[0]").saveAs("wid2")))
        .exec(session => {
          val str = session("wid2").as[String]
          if (str.length < 5) {
            session.set("loop2", true)
          } else {
            session.set("loop2", false)
          }
        })
    }
    .pause(1)
    .exec(http("solve_task_start")
      .post("/resource/workitem/start")
      .formParam("workItemId", "${wid2}")
      .formParam("workerId", "account-3"))
    .pause(10)
    .exec(http("solve_task_complete")
      .post("/resource/workitem/complete")
      .formParam("workItemId", "${wid2}")
      .formParam("workerId", "account-3"))
    // *************************** SolveVote Task Request ***************************
    .exec(session => {
      session.set("loop3", true)
    })
    .doWhile("${loop3}") {
      pause(10)
        .exec(http("get_wid")
          .post("/resource/workitem/getAll")
          .formParam("processInstanceId", "${processInstanceId}")
          .check(jsonPath("$..data[0].WorkItemId[0]").saveAs("wid3")))
        .exec(session => {
          val str = session("wid3").as[String]
          if (str.length < 5) {
            session.set("loop3", true)
          } else {
            session.set("loop3", false)
          }
        })
    }
    .pause(1)
    .exec(http("solvevote_task_start")
      .post("/resource/workitem/start")
      .formParam("workItemId", "${wid3}")
      .formParam("workerId", "account-3"))
    .pause(10)
    .exec(http("solvevote_task_complete")
      .post("/resource/workitem/complete")
      .formParam("workItemId", "${wid3}")
      .formParam("workerId", "account-3"))
    // *************************** GetBestSolution Task Request ***************************
    .exec(session => {
      session.set("loop4", true)
    })
    .doWhile("${loop4}") {
      pause(10)
        .exec(http("get_wid")
          .post("/resource/workitem/getAll")
          .formParam("processInstanceId", "${processInstanceId}")
          .check(jsonPath("$..data[0].WorkItemId[0]").saveAs("wid4")))
        .exec(session => {
          val str = session("wid4").as[String]
          if (str.length < 5) {
            session.set("loop4", true)
          } else {
            session.set("loop4", false)
          }
        })
    }
    .pause(1)
    .exec(http("getbestsolution_task_start")
      .post("/resource/workitem/start")
      .formParam("workItemId", "${wid4}")
      .formParam("workerId", "account-3"))
    .pause(10)
    .exec(http("getbestsolution_task_complete")
      .post("/resource/workitem/complete")
      .formParam("workItemId", "${wid4}")
      .formParam("workerId", "account-3"))

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
  // setUp(
  // 		scn.inject(
  // 			// constantUsersPerSec(2) during (2 minutes))
  //       rampUsers(300) during (1 minutes))
  // 	)
  //   .throttle(
  // 		reachRps(50) in (10 seconds),
  // 		holdFor(10 minutes)
  // 	)
  //   .protocols(httpProtocol)
}
