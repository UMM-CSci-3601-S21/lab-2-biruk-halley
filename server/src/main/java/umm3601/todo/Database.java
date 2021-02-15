package umm3601.todo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
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

    // Limit Todo stream

    if (queryParams.containsKey("limit")) {
      String limitParam = queryParams.get("limit").get(0);

      try {
        int targetAge = Integer.parseInt(limitParam);
        filteredTodos = limitTodos(filteredTodos, targetAge);
      } catch (NumberFormatException e) {
        throw new BadRequestResponse("Specified limit '" + limitParam + "' can't be parsed to an integer");
      }
    }
    // owner filter

    // category filter

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

    return filteredTodos;
  }

  /**
   * Used in listTodos(). Limits amount of todo JSON objects displayed.
   * @param todos
   * @param targetLimit
   * @return array of limited todos
   */
  public Todo[] limitTodos(Todo[] todos, int targetLimit) {
    return Arrays.stream(todos).limit(targetLimit).toArray(Todo[]::new);
  }

  /**
   * Used in listTodos(). Searches for todo JSON objects with designated status.
   * @param todos
   * @param status
   * @return array of todos with designated status (Complete, Incomplete).
   */
  public Todo[] filterTodosByStatus(Todo[] todos, String status) {
    return Arrays.stream(todos).filter(each -> each.status == "complete".equals(status))
    .toArray(Todo[]::new);
  }

  /**
   * Used in listTodos(). Searches body of todo JSON object to see if it contains given key word.
   * @param todos
   * @param keyWord
   * @return array of todos with bodies containing given key word.
   */
  public Todo[] searchTodosByKeyWord(Todo[] todos, String keyWord){
    return Arrays.stream(todos).filter(x -> x.body.contains(keyWord)).toArray(Todo[]::new);
  }
}
