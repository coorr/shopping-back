package shopping.coor.common.application.command;

public interface Command<T extends CommandModel> {
    void execute(T model);
}
