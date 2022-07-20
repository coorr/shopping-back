package shopping.coor.kernel.application.command;

public class CommandExecutor<T extends CommandModel> {
    private final Command<T> command;

    private final T t;

    public CommandExecutor(Command<T> command, T t) {
        this.command = command;
        this.t = t;
    }

    public void invoke() {
        command.execute(t);
    }
}
