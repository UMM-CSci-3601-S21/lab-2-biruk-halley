package umm3601.todo;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;


public class TodoController {

  private Database database;

  public TodoController(Database database) {
    this.database = database;
  }

  /**
   * This method retrieves a todo with a given ID
   * @param ctx: The context that includes the passed ID
   */

  public void getTodo(Context ctx) {
    String id = ctx.pathParam("id", String.class).get();
    Todo todo = database.getTodo(id);
    if (todo != null) {
      ctx.json(todo);
      ctx.status(201);
    } else {
      throw new NotFoundResponse("No todo with id " + id + " was found.");
    }
  }

   /**
   * Get a JSON response with a list of all the todos in the database.
   *
   * @param ctx : the http context
   */
  public void getTodos(Context ctx) {
    Todo[] todos = database.listTodos(ctx.queryParamMap());
    ctx.json(todos);
  }
}
