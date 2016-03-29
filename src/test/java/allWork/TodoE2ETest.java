package allWork;

/**
 * Created by Winterfall on 29.03.2016.
 */

import allWork.categories.Smoke;
import allWork.configs.BaseTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pages.TodoMVCPage;

@Category(Smoke.class)
public class TodoE2ETest extends BaseTest {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testTaskLifeCycle() {
        page.givenAtAll();
        page.add("1");
        page.startEdit("1", "a").pressEnter();

        //complete
        page.toggle("a");
        page.assertTasks("a");

        page.filterActive();
        page.assertNoVisibleTask();

        page.add("b");
        page.assertVisibleTasks("b");
        page.assertItemsLeft(1);

        //complete all
        page.toggleAll();
        page.assertNoVisibleTask();

        page.filterCompleted();
        page.assertTasks("a", "b");

        //reopen task
        page.toggle("b");
        page.assertVisibleTasks("a");

        page.clearCompleted();
        page.assertNoVisibleTask();

        page.filterAll();
        page.assertTasks("b");

        page.delete("b");
        page.assertNoTask();
    }
}