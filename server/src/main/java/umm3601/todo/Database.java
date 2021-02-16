package umm3601.todo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import io.javalin.http.BadRequestResponse;

/**
 * A fake database of todos
 */
public class Database {

  // List holding all the todos in the database.
  private Todo[] allTodos;

  /**
   * Constructor for the database, loads all data from the local file.
   *
   * @param todoDataFile
   * @throws IOException
   */
  public Database(String todoDataFile) throws IOException {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(todoDataFile));
    allTodos = gson.fromJson(reader, Todo[].class);
  }

  /**
   * Returns the size of the entire database of todos
   *
   * @return the size
   */
  public int size() {
    return allTodos.length;
  }

  /**
   * Get the single todo specified by the given ID. Return `null` if there is no
   * todo with that ID.
   *
   * @param id the ID of the desired todo
   * @return the todo with the given ID, or null if there is no todo with that ID
   */
  public Todo getTodo(String id) {
    return Arrays.stream(allTodos).filter(x -> x._id.equals(id)).findFirst().orElse(null);
  }

  /**
   * Retrieves all ToDo data from todo.json file
   *
   * @param queryParams list of parameters to filter by
   * @return array of ToDo data
   */

  public Todo[] listTodos(Map<String, List<String>> queryParams) {
    Todo[] filteredTodos = allTodos;

    // owner filter
    if (queryParams.containsKey("owner")) {
      String statusParam = queryParams.get("owner").get(0);

      filteredTodos = filterTodosByOwner(filteredTodos, statusParam);
    }

    // category filter

    if (queryParams.containsKey("category")) {
      String categoryParam = queryParams.get("category").get(0);

      filteredTodos = filterTodosByCategory(filteredTodos, categoryParam);
    }

    // status filter

    if (queryParams.containsKey("status")) {
      String statusParam = queryParams.get("status").get(0);

      filteredTodos = filterTodosByStatus(filteredTodos, statusParam);
    }

    // contains filter

    if (queryParams.containsKey("contains")) {
      String containsParam = queryParams.get("contains").get(0);

      filteredTodos = searchTodosByKeyWord(filteredTodos, containsParam);
    }

    // orderBy

    if (queryParams.containsKey("orderBy")) {
      String containsParam = queryParams.get("orderBy").get(0);

      filteredTodos = sortByAttribute(filteredTodos, containsParam);
    }

    // Limit

    if (queryParams.containsKey("limit")) {
      String limitParam = queryParams.get("limit").get(0);

      try {
        int targetAge = Integer.parseInt(limitParam);
        filteredTodos = limitTodos(filteredTodos, targetAge);
      } catch (NumberFormatException e) {
        throw new BadRequestResponse("Specified limit '" + limitParam + "' can't be parsed to an integer");
      }
    }

    return filteredTodos;
  }

  /**
   * Used in listTodos(). Limits amount of todo JSON objects displayed.
   *
   * @param todos
   * @param targetLimit
   * @return array of limited todos
   */
  public Todo[] limitTodos(Todo[] todos, int targetLimit) {
    return Arrays.stream(todos).limit(targetLimit).toArray(Todo[]::new);
  }

  /**
   * Used in listTodos(). Searches for todo JSON objects with designated status.
   *
   * @param todos
   * @param status
   * @return array of todos with designated status (Complete, Incomplete).
   */
  public Todo[] filterTodosByStatus(Todo[] todos, String status) {
    return Arrays.stream(todos).filter(each -> each.status == "complete".equals(status)).toArray(Todo[]::new);
  }

  /**
   * Used in listTodos(). Searches body of todo JSON object to see if it contains
   * given key word.
   *
   * @param todos
   * @param keyWord
   * @return array of todos with bodies containing given key word.
   */
  public Todo[] searchTodosByKeyWord(Todo[] todos, String keyWord) {
    return Arrays.stream(todos).filter(x -> x.body.contains(keyWord)).toArray(Todo[]::new);
  }

  /**
   * / * Used in listTodos(). Limits results to be from a single owner.
   *
   * @param todos:
   * @param owner
   * @return
   */
  public Todo[] filterTodosByOwner(Todo[] todos, String owner) {
    return Arrays.stream(todos).filter(each -> each.owner.toLowerCase().equals(owner.toLowerCase()))
        .toArray(Todo[]::new);
  }

  /*
   * Used in listTodos(). Searches for todo JSON objects with desired category.
   *
   * @param todos
   *
   * @param category
   *
   * @return array of todos with desired category.
   */
  public Todo[] filterTodosByCategory(Todo[] todos, String category) {
    return Arrays.stream(todos).filter(x -> x.category.equals(category)).toArray(Todo[]::new);
  }

  public Todo[] sortByAttribute(Todo[] todos, String attr) {
    Todo[] copy = todos.clone();
    Arrays.sort(copy, new Comparator<Todo>() {
      @Override
      public int compare(Todo o1, Todo o2) {
        switch (attr) {
          case "owner": {
            return o1.owner.compareTo(o2.owner);
          }
          case "body": {
            return o1.body.compareTo(o2.body);
          }
          case "category": {
            return o1.category.compareTo(o2.category);
          }
          case "status": {
            if (o1.status == o2.status)
              return 0;
            else if (o1.status && !o2.status)
              return 1;
            else
              return -1;
          }
          default:
            return -1;
        }
      }
    });
    return copy;
  }
}
