import Data.Interfaces.IDB;
import Data.PostgresDB;
import controllers.ProductController;
import controllers.UserController;
import controllers.interfaces.IProductController;
import controllers.interfaces.IUserController;
import repositories.ProductRepository;
import repositories.UserRepository;
import repositories.interfaces.IProductRepository;
import repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "12345678", "tea_shop");

        IUserRepository user_repo = new UserRepository(db);
        IUserController user_controller = new UserController(user_repo);
        IProductRepository product_repo = new ProductRepository(db);
        IProductController product_controller = new ProductController(product_repo);
        MyApplication app = new MyApplication(user_controller, product_controller);
        app.start();
        db.close();
    }
}