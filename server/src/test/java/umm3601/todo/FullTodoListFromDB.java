package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;


public class FullTodoListFromDB {

  @Test
  public void totalToCount() throws IOException {
    Database db = new Database("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    assertEquals(300, allTodos.length, "Incorrect total number of Todos");
  }

  @Test
  public void firstToInFullList() throws IOException {
    Database db = new Database("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo firstTodo = allTodos[0];
    assertEquals("Blanche", firstTodo.owner, "Incorrect owner");
    assertEquals(false, firstTodo.status, "Incorrect status");
    assertEquals("In sunt ex non tempor cillum commodo amet incididunt anim qui commodo quis. Cillum non labore ex sint esse.", firstTodo.body, "Incorrect body");
    assertEquals("software design", firstTodo.category, "Incorrect category");

    Todo secondTodo = allTodos[1];
    assertEquals("Fry", secondTodo.owner, "Incorrect owner");
    assertEquals(false, secondTodo.status, "Incorrect status");
    assertEquals("Ipsum esse est ullamco magna tempor anim laborum non officia deserunt veniam commodo. Aute minim incididunt ex commodo.", secondTodo.body, "Incorrect body");
    assertEquals("video games", secondTodo.category, "Incorrect category");

  }
}
