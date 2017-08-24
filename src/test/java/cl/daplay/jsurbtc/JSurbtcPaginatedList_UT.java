package cl.daplay.jsurbtc;

import org.junit.Test;

import java.util.Collections;

import static cl.daplay.jsurbtc.JSurbtcPaginatedList.indexInPage;
import static cl.daplay.jsurbtc.JSurbtcPaginatedList.isValidIndex;
import static cl.daplay.jsurbtc.JSurbtcPaginatedList.pageForIndex;
import static java.util.Collections.emptyList;
import static org.junit.Assert.*;

public class JSurbtcPaginatedList_UT {

    @Test
    public void empty() {
        final JSurbtcPaginatedList<Object> emptyList = new JSurbtcPaginatedList<>(emptyList(), 0, 0, __ -> Collections.emptyList());

        assertEquals(0, emptyList.size());
        assertTrue(emptyList.isEmpty());

        for (Object o : emptyList) {
            fail("empty list has items");
        }
    }

    @Test
    public void test_page_for_index() {
        assert 0 == pageForIndex(0, 5);
        assert 0 == pageForIndex(1, 5);
        assert 0 == pageForIndex(2, 5);
        assert 0 == pageForIndex(3, 5);
        assert 0 == pageForIndex(4, 5);
        assert 1 == pageForIndex(5, 5);
    }

    @Test
    public void test_index_in_page() {
        assert 0 == indexInPage(0, 5);
        assert 1 == indexInPage(1, 5);
        assert 2 == indexInPage(2, 5);
        assert 3 == indexInPage(3, 5);
        assert 4 == indexInPage(4, 5);
        assert 0 == indexInPage(5, 5);
    }

    @Test
    public void test_is_valid_index() {
        assert !isValidIndex(-1, 5);
        assert isValidIndex(0, 5);
        assert isValidIndex(1, 5);
        assert isValidIndex(2, 5);
        assert isValidIndex(3, 5);
        assert isValidIndex(4, 5);
        assert !isValidIndex(5, 5);
    }

}
