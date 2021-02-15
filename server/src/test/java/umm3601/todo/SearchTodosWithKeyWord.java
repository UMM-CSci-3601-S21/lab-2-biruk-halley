package umm3601.todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTodosWithKeyWord {

  @Test

  public void searchByKeyWord() throws IOException {
    Database db = new Database("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] searchedTodos = db.searchTodosByKeyWord(allTodos,"qui");
    for (Todo todo: searchedTodos){
    assertTrue(todo.body.contains("qui"));
    }
  }

  @Test

  public void filterSearchByKeyword() throws IOException {
    Database db = new Database("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("contains", Arrays.asList(new String[] { "qui" }));
    Todo[] todosWithQui = db.listTodos(queryParams);
    for (Todo todo: todosWithQui){
      assertTrue(todo.body.contains("qui"));
      }

      queryParams.put("contains", Arrays.asList(new String[] { "incididunt" }));
    Todo[] todosWithIncididunt = db.listTodos(queryParams);
    for (Todo todo: todosWithIncididunt){
      assertTrue(todo.body.contains("incididunt"));
      }

  }
}
