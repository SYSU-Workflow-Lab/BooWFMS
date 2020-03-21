package cn.edu.sysu.workflow.resource.core.principle;

import cn.edu.sysu.workflow.common.enums.PrincipleDialectType;

/**
 * Author: Rinkako, Skye
 * Date  : 2018/2/6
 * Usage : Parser for task resourcing principle descriptor.
 */
public final class PrincipleParser {

    /**
     * Parse principle descriptor to Principle object.
     *
     * @param principleDescriptor principle descriptor string
     * @return parsed object
     */
    public static Principle Parse(String principleDescriptor) {
        return PrincipleParser.parser.Parse(principleDescriptor);
    }

    /**
     * Set parser grammar dialect.
     *
     * @param type grammar dialect enum
     */
    public static void SetParser(PrincipleDialectType type) {
        if (PrincipleParser.parser == null || PrincipleParser.dialectType != type) {
            PrincipleParser.dialectType = type;
            switch (type) {
                case SimplePrincipleGrammar:
                    PrincipleParser.parser = new SimplePrincipleGrammar();
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    /**
     * Current parser.
     */
    private static PrincipleGrammar parser = new SimplePrincipleGrammar();

    /**
     * Current parser dialect type.
     */
    private static PrincipleDialectType dialectType = PrincipleDialectType.SimplePrincipleGrammar;
}
