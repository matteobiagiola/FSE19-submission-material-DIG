package graph;

import org.jgrapht.io.ComponentNameProvider;

import java.util.stream.Collectors;

public class EdgeLabelProvider implements ComponentNameProvider<Connection> {

    @Override
    public String getName(Connection connection) {
        StringBuilder builder = new StringBuilder();
        builder.append(connection.getMethod().getSimpleName() + "(");
        String parameters = connection.getMethod().getParameters().stream()
                .map(ctParameter -> {
                    return ctParameter.getSimpleName();
                }).collect(Collectors.joining(","));
        builder.append(parameters + ")");
        return builder.toString();
    }
}
