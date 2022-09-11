package com.example.app.views.list;

import com.example.app.data.entity.Person;
import com.example.app.views.list.form.PersonForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

    @Autowired
    private ListView listView;

    @Test
    public void formShowWhenContactSelected(){
        Grid<Person> grid = listView.grid;
        Person firstPerson = getFirstItem(grid);

        PersonForm form = listView.form;

        Assert.assertFalse((form.isVisible()));
        grid.asSingleSelect().setValue((firstPerson));

        Assert.assertTrue(form.isVisible());
//        Assert.assertEquals(firstPerson.getfirstName(), form.firstName.getValue());
        

    }

    private Person getFirstItem(Grid<Person> grid) {
        return ((ListDataProvider<Person>)grid.getDataProvider()).getItems().iterator().next();     // kiveszi az első elemet a gridből
    }
}
