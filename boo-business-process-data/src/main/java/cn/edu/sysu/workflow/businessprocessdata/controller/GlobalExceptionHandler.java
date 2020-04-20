package cn.edu.sysu.workflow.businessprocessdata.controller;

import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.common.entity.exception.ServiceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller统一异常处理
 *
 * @author Skye
 * Created on 2020/1/2
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BooReturnForm serviceFailure(ServiceFailureException e) {
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage(e.toString());
        return booReturnForm;
    }
    
}
