import java.util.ArrayList;

public class InitListener extends CBaseListener {

    private final ArrayList<Range> ranges = new ArrayList<Range>();

    @Override public void enterBlockItemList(CParser.BlockItemListContext ctx) {
        ranges.add(new Range(ctx.start.getLine(), ctx.stop.getLine()));
    }

    @Override public void enterStatement(CParser.StatementContext ctx) {
        if (ctx.parent.getClass().toString().equals("class CParser$LabeledStatementContext"))
            ranges.add(new Range(ctx.start.getLine(), ctx.stop.getLine()+1));
    }

    public ArrayList<Range> getRanges() {
        return ranges;
    }
}