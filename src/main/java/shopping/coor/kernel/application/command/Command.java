package shopping.coor.kernel.application.command;

public interface Command<T extends CommandModel> {
    void execute(T model);
}
