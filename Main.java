import controllers.CategoryController;
import controllers.ProductController;
import controllers.UserController;
import Data.PostgresDB;
import Data.Interfaces.IDB;
import controllers.interfaces.IUserController;
import repositories.CategoryRepository;
import repositories.ProductRepository;
import repositories.UserRepository;
import repositories.interfaces.ICategoryRepository;
import repositories.interfaces.IProductRepository;
import repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "12345678", "tea_shop");
        IUserRepository user_repo = new UserRepository(db);
        UserController user_controller = new UserController(user_repo); // <-- исправлено
        IProductRepository product_repo = new ProductRepository(db);
        ProductController product_controller = new ProductController(product_repo); // <-- исправлено
        ICategoryRepository category_repo = new CategoryRepository(db);
        CategoryController category_controller = new CategoryController(category_repo); // <-- исправлено
        MyApplication app = new MyApplication((IUserController) user_controller, product_controller, category_controller);
        app.start();
        db.close();
    }
}
