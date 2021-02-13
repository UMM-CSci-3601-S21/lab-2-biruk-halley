package umm3601.todo;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;


public class TodoController {

  private Database database;

  public TodoController(Database database) {
    this.database = database;
  }

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
}
