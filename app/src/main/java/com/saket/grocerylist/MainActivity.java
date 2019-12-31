package com.saket.grocerylist;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


/**
 * Learning from this ap:
 * 1. There are 3 ways to get FragmentManager, getSupportFragmentManager, getFragmentManager and getChildFragmentManager.
 * 2. getSupportFragmentManager is called at Activity level and the rest in a fragment
 * 3. getFragmentManager in fragment returns the activity's fragmentManager instance
 * 4- GetChildFragmentManger in fragment returns its own fragmentManager instance
 * 5. addToBackStack() - The FragmentTransaction has its backstack where you can save transactions using this method.
 * 6. The backstack works in a lifo manner. So if you press back on the current view, it is replaced with the last
 * view that was added to the backstack.
 * 7. You can provide onClick listeners from layout using android:onClick tag and defining a method in code
 * which takes a single view parameter.
 * 8. Room DB implementation. @Database, @Entity, @Dao.(Evernote)
 * 9. Use singleton to get instance of RoomDB connection to DB.
 * 10. Asynctask revisited. As DB operations cannot be done on main thread.
 * 11. Asynctask should be static warning - Nested class, inner class, nested-static class.(Evernote)
 * 12. Asynctask should be static warning - Memory managment in Android, what are memory leaks
 * and how to prevent memory leaks. (Evernote)
 * 13. Memory management - RAM, Virtual memory, Paging, Page table, MMU, logical address (Virtual memory), phyical address (RAM),
 * zRAM, Garbage Collection, heap vs stack memory. Memory profiler etc. (Evernote)
 * 14. assert object!= null;
 * 15. Update fragment view only in onResume() as it will not work in onCreateView().
 * 16. list item onclicklistener implemented in onBind method of adapter.
 * 17. To update items in a recyclerview, you can update the list as follows - listItems.clear() and listItem.AddAll().
 * Directly setting the list value like listItems = getLatestList() will not trigger update of listadapter.
 */
public class MainActivity extends AppCompatActivity implements CreateProductFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Add HomeListFragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, HomeListFragment.createInstance(), "");
        fragmentTransaction.commit();


        //Create instance of Shape
        Shape square = new Square();
        Shape circle = new Circle();

        square.calculateArea();
        circle.calculateArea();

        AbstractShape abstractShape = new ChildShape();
        abstractShape.calculateAbstractArea();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //android:onclick from xml layout file
    public void onCreateListClicked(View view) {
        //Go to Create list fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, CreateListFragment.getInstance(null))
                .addToBackStack(null)
                .commit();
    }

    //Just trying out some things related to abstract class and inheritance here...
    abstract class AbstractShape {

        abstract void calculateAbstractArea();
    }


    class ChildShape extends AbstractShape{

        @Override
        void calculateAbstractArea() {
            Log.d("TAG", "This is a ChildSquare");
        }
    }

    class Shape {
        void calculateArea() { };
    }

    class Square extends Shape {

        @Override
        void calculateArea() {
            Log.d("TAG", "This is a square");
        }
    }

    class Circle extends Shape {

        @Override
        void calculateArea() {
            Log.d("TAG", "This is a Circle");
        }
    }

}
