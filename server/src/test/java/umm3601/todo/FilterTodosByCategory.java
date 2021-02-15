package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class FilterTodosByCategory {

  @Test

  public void todosByCategory() throws IOException {
    Database db = new Database("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] softwareDesignTodos = db.filterTodosByCategory(allTodos,"software design");
    for (Todo todo: softwareDesignTodos){
    assertEquals("software design",todo.category,"Incorrect category for listed todos.");
    }
  }

  @Test

  public void filterTodosByCategory() throws IOException {
    Database db = new Database("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("category", Arrays.asList(new String[] { "homework" }));
    Todo[] homeworkTodos = db.listTodos(queryParams);
    for (Todo todo: homeworkTodos){
      assertEquals("homework",todo.category,"Incorrect category for listed todos.");
      }

      queryParams.put("category", Arrays.asList(new String[] { "video games" }));
    Todo[] videoGamesTodos = db.listTodos(queryParams);
    for (Todo todo: videoGamesTodos){
      assertEquals("video games",todo.category,"Incorrect category for listed todos.");
      }

  }
}
