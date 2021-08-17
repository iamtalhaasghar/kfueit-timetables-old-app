package pk.edu.kfueit.timetable_app.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pk.edu.kfueit.timetable_app.CustomAdapter;
import pk.edu.kfueit.timetable_app.MainActivity;
import pk.edu.kfueit.timetable_app.R;
import pk.edu.kfueit.timetable_app.TimeTable;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {



    JSONArray lectures;
    RecyclerView recyclerView;

    public PlaceholderFragment(){

    }

    public PlaceholderFragment(JSONArray lectures) {
        this.lectures = lectures;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {



        View root = inflater.inflate(R.layout.fragment_main, container, false);


        // get the reference of RecyclerView
        recyclerView = root.findViewById(R.id.recycler_view);
        // set a LinearLayoutManager with default vertical orientaion
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView


        // call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = null;

        if (lectures != null) {
            customAdapter = new CustomAdapter(getContext(), lectures);
            recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        }




        return root;
    }
}