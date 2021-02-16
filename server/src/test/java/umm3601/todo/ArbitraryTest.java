package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;

public class ArbitraryTest {
  String[][] testScenarios = new String[][] { new String[] { "Blanche", "Completed", "video games", "10" },
      new String[] { "Fry", "Incomplete", "software design", "5" },
      new String[] { "Dawn", "Complete", "homework", "20" }, new String[] { "Barry", "Incomplete", "video games", "3" },
      new String[] { "Workman", "Complete", "homework", "6" } };

  public void arbitraryTest() throws IOException {
    Database db = new Database("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    for (String[] tests : testScenarios) {
      Todo[] ownerTodos = db.filterTodosByOwner(allTodos, tests[0]);
      Todo[] statusTodos = db.filterTodosByStatus(ownerTodos, tests[1]);
      Todo[] attributeTodos = db.sortByAttribute(statusTodos, tests[2]);
      Todo[] limitTodos = db.limitTodos(attributeTodos, Integer.parseInt(tests[3]));

      for (Todo todo : limitTodos) {
        assertEquals(todo.owner, tests[0]);
        assertEquals(tests[1].equals("complete") ? true : false, todo.status);
        assertEquals(todo.category, tests[2]);
        assertTrue(limitTodos.length <= Integer.parseInt(tests[3]));
      }
    }
  }
}
