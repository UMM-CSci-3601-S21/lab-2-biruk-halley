package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class LimitTodos {

  @Test
  public void limitTodos() throws IOException {
    Database db = new Database("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] limit27 = db.limitTodos(allTodos, 27);
    assertEquals(27, limit27.length, "Incorrect number of Todos listed.");

    Todo[] limit12 = db.limitTodos(allTodos, 12);
    assertEquals(12, limit12.length, "Incorrect number of Todos listed.");
  }

  @Test
  public void listTodosWithLimit() throws IOException {
    Database db = new Database("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("limit", Arrays.asList(new String[] { "14" }));
    Todo[] fourteenTodos = db.listTodos(queryParams);
    assertEquals(14, fourteenTodos.length, "Incorrect number of Todos listed.");

    queryParams.put("limit", Arrays.asList(new String[] { "2" }));
    Todo[] twoTodos = db.listTodos(queryParams);
    assertEquals(2, twoTodos.length, "Incorrect number of Todos listed.");
  }
}
