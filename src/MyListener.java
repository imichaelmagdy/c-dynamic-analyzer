import org.antlr.v4.runtime.Token;

public class MyListener extends CBaseListener {

    private final StringBuilder code;
    private final String fileName;
    private int offset = 0;
    private int executionCounter = 0;

    public MyListener(StringBuilder code, String fileName) {
        this.code = code;
        this.fileName = fileName;
    }

    @Override public void enterBlockItemList(CParser.BlockItemListContext ctx) {
//        System.out.println(ctx.getText());
        if (ctx.parent.parent.getText().substring(0, 7).equals("intmain")) {
            insertToCode(ctx.getParent().getParent().getParent().start, "FILE *DAOutputPTR;\n");
            insertToCode(ctx.start, "fclose(fopen(\"../test-cases/temp/" + fileName + ".txt\", \"w\"));\n\tDAOutputPTR = fopen(\"../test-cases/temp/" + fileName + ".txt\", \"a\");\n");
        }
            //        System.out.println(ctx.getText());
        insertToCode(ctx.start, "fprintf(DAOutputPTR, \"" + executionCounter++ + "\\n\");");
    }

    @Override public void enterStatement(CParser.StatementContext ctx) {
        if (ctx.parent.getClass().toString().equals("class CParser$LabeledStatementContext"))
            insertToCode(ctx.start, "fprintf(DAOutputPTR, \"" + executionCounter++ + "\\n\");");
//            System.out.println(ctx.getText());
    }

    @Override public void enterJumpStatement(CParser.JumpStatementContext ctx) {
        if (ctx.parent.parent.parent.parent.parent.getText().substring(0, 7).equals("intmain"))
            insertToCode(ctx.start, "fclose(DAOutputPTR);");
//            System.out.println(ctx.getText());
    }

    private void insertToCode(Token start, String text) {
        int index = start.getStartIndex() + offset;
        String cleanText = text + "\n" + getIndentation(index);
        code.insert(index, cleanText);
        offset += cleanText.length();
    }

    private String getIndentation(int index) {
        int count = 0;
        while (code.charAt(--index) == ' ')
            count++;
        return " ".repeat(count);
    }

    public int getExecutionCounter() {
        return executionCounter;
    }
}