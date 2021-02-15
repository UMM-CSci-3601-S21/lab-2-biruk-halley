package umm3601.todo;


import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class FilterTodosByOwner {
  @Test
  public void filterTodosByOwner() throws IOException {
    Database db = new Database("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] blanchesTodos = db.filterTodosByOwner(allTodos, "Blanche");
    for (Todo todo : blanchesTodos) {
      assertEquals(todo.owner,"Blanche");
    }

    Todo[] frysTodos = db.filterTodosByOwner(allTodos, "Fry");
    for (Todo todo : frysTodos) {
      assertEquals(todo.owner,"Fry");
    }
  }

  public void listUsersWithOwnerFilter() throws IOException {
    Database db = new Database("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("owner", Arrays.asList(new String[] { "Blanche" }));
    Todo[] blanchesTodos = db.listTodos(queryParams);
    for (Todo todo: blanchesTodos) {
        assertEquals(todo.owner,"Blanche");
    }
  }
}
