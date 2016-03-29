package allWork;

/**
 * Created by Winterfall on 29.03.2016.
 */

import allWork.categories.Buggy;
import allWork.configs.BaseTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pages.TodoMVCPage;
import allWork.categories.Smoke;

import static com.codeborne.selenide.Selenide.$;
import static pages.TodoMVCPage.TaskType.*;

public class TodoOperationsAtAllFilterTest extends BaseTest {

    TodoMVCPage page = new TodoMVCPage();

    @Category(Smoke.class)
    @Test
    public void testCompleteAllAtAll() {
        page.givenAtAll(ACTIVE, "a", "b");

        page.toggleAll();
        page.assertItemsLeft(0);
    }

    @Category(Smoke.class)
    @Test
    public void testClearCompletedAtAll() {
        page.given(page.aTask("a", COMPLETED),
                page.aTask("b", ACTIVE),
                page.aTask("c", COMPLETED));

        page.clearCompleted();
        page.assertTasks("b");
        page.assertItemsLeft(1);
    }

    @Test
    public void testReopenAllAtAllAndMoveToCompleted() {
        page.givenAtAll(COMPLETED, "a", "b");

        page.toggleAll();
        page.assertTasks("a", "b");
        page.assertItemsLeft(2);

        page.filterCompleted();
        page.assertNoVisibleTask();
    }

    //buggy
    @Category(Buggy.class)
    @Test
    public void testCancelEditByEscAtAll() {
        page.given(page.aTask("a", ACTIVE), page.aTask("b", COMPLETED));

        page.startEdit("a", "a edit cancelled").pressEscape();
        page.assertTasks("a", "a edit cancelled"); //!
        page.assertItemsLeft(1);
    }

    @Category(Smoke.class)
    @Test
    public void testEditByPressTabAtAll() {
        page.given(page.aTask("a", COMPLETED), page.aTask("b", ACTIVE));

        page.startEdit("a", "a edited").pressTab();
        page.assertTasks("a edited", "b");
        page.assertItemsLeft(1);
    }

    @Test
    public void testEditByPressOutsideAtAll() {
        page.givenAtAll(ACTIVE, "a");

        page.startEdit("a", "a edited");
        $("#header").click();
        page.assertVisibleTasks("a edited");
        page.assertItemsLeft(1);
    }

    @Test
    public void testEditByRemovalTextAtAll() {
        page.given(page.aTask("a", COMPLETED), page.aTask("b", ACTIVE));

        page.startEdit("a", "").pressEnter();
        page.assertTasks("b");
        page.assertItemsLeft(1);
    }
}