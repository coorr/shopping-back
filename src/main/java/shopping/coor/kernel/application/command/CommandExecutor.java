package shopping.coor.kernel.application.command;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommandExecutor<T extends CommandModel> {
    private final Command<T> command;

    private final T model;

    public void invoke() {
        this.command.execute(this.model);
    }
}
