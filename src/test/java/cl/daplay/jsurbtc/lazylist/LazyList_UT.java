package cl.daplay.jsurbtc.lazylist;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

import static cl.daplay.jsurbtc.lazylist.LazyList.*;
import static java.util.Collections.emptyList;

public class LazyList_UT {

    @Test
    public void empty() {
        final LazyList<Object> emptyList = new LazyList<>(emptyList(), __ -> Collections.emptyList(), 0, 0);

        Assert.assertEquals(0, emptyList.size());
        Assert.assertTrue(emptyList.isEmpty());

        for (Object o : emptyList) {
            Assert.fail("empty list has items");
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
