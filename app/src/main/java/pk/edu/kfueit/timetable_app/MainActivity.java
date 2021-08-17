package pk.edu.kfueit.timetable_app;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import pk.edu.kfueit.timetable_app.ui.main.PlaceholderFragment;
import pk.edu.kfueit.timetable_app.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://my.kfueit.edu.pk/users/testtable?filter=class&timetablename=KFUEIT+Spring+2020+Timetable+%28w.e.f.+04+March+2020%29&department=&sets=BS-COSC-6B&teacher=&room=&subject=";
        TimeTable scraper = new TimeTable();

        try {
            JSONObject timeTable = scraper.execute(url).get();
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), timeTable);
            ViewPager viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Pressing this button will download the time table. (Work in Progress)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}