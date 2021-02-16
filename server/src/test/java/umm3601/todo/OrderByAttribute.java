package umm3601.todo;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import java.util.HashMap;


public class OrderByAttribute {

  String[] attributes = new String[] { "category", "owner", "status", "body" };

  @Test

  public void orderByAttribute() throws IOException {
    Database db = new Database("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] ownerTodos = db.sortByAttribute(allTodos, "owner");
    for (int i = 0; i < ownerTodos.length - 1; i++) {
      Todo currentTodo = ownerTodos[i];
      Todo nextTodo = ownerTodos[i + 1];

      assertTrue(currentTodo.owner.compareTo(nextTodo.owner) <= 0);

    }

    Todo[] statusTodos = db.sortByAttribute(allTodos, "status");
    for (int i = 0; i < statusTodos.length - 1; i++) {
      Todo currentTodo = statusTodos[i];
      Todo nextTodo = statusTodos[i + 1];

      assertTrue(!currentTodo.status && nextTodo.status || currentTodo.status == nextTodo.status);

    }

    Todo[] categoryTodos = db.sortByAttribute(allTodos, "category");
    for (int i = 0; i < categoryTodos.length - 1; i++) {
      Todo currentTodo = categoryTodos[i];
      Todo nextTodo = categoryTodos[i + 1];

      assertTrue(currentTodo.category.compareTo(nextTodo.category) <= 0);

    }

    Todo[] bodyTodos = db.sortByAttribute(allTodos, "body");
    for (int i = 0; i < bodyTodos.length - 1; i++) {
      Todo currentTodo = bodyTodos[i];
      Todo nextTodo = bodyTodos[i + 1];

      assertTrue(currentTodo.body.compareTo(nextTodo.body) <= 0);

    }
  }

  @Test
  public void sortByAttributeDatabase() throws IOException {
    // Database db = new Database("/todos.json");
    // Map<String, List<String>> queryParams = new HashMap<>();

    // for (String attr : attributes) {
    //   queryParams.put("orderBy", Arrays.asList(new String[] { attr }));
    //   Todo[] sortedTodos = db.listTodos(queryParams);
    //   for (int i = 0; i < sortedTodos.length - 1; i++) {
    //     Todo currentTodo = sortedTodos[i];
    //     Todo nextTodo = sortedTodos[i + 1];

    //     switch (attr) {
    //       case "category":
    //         assertTrue(currentTodo.category.compareTo(nextTodo.category) <= 0);
    //         // case "status":
    //         // assertTrue(!currentTodo.status && nextTodo.status || currentTodo.status ==
    //         // nextTodo.status);
    //         // case "owner":
    //         // assertTrue(currentTodo.owner.compareTo(nextTodo.owner) <= 0);
    //         // case "body":
    //         // assertTrue(currentTodo.body.compareTo(nextTodo.body) <= 0);
    //     }
    //   }
    // }

  }

}
