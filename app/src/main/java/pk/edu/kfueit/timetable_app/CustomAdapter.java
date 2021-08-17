package pk.edu.kfueit.timetable_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter {

    JSONArray lectures;
    Context context;

    public CustomAdapter(Context context, JSONArray lectures) {
        this.context = context;
        this.lectures = lectures;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        // set the data in items
        try {
            JSONObject lecture = lectures.getJSONObject(position);
            myViewHolder.courseName.setText(lecture.getString(TimeTable.SUBJECT));
            myViewHolder.teacherName.setText(lecture.getString(TimeTable.TEACHER));
            myViewHolder.roomName.setText(lecture.getString(TimeTable.ROOM));
            myViewHolder.startTime.setText(lecture.getString(TimeTable.START_TIME));
            myViewHolder.endTime.setText(lecture.getString(TimeTable.END_TIME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // implement setOnClickListener event on item view.
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click

                //Intent intent = new Intent(context, SecondActivity.class);
                //intent.putExtra("image", personImages.get(position)); // put image data in Intent
                //context.startActivity(intent); // start Intent
                Toast.makeText(context,"clicked",  Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lectures.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView courseName;
        TextView teacherName;
        TextView roomName;
        TextView startTime;
        TextView endTime;
        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            courseName = (TextView) itemView.findViewById(R.id.course_name);
            teacherName = (TextView) itemView.findViewById(R.id.teacher_name);
            roomName = (TextView) itemView.findViewById(R.id.room_name);
            startTime = (TextView) itemView.findViewById(R.id.start_time);
            endTime = (TextView) itemView.findViewById(R.id.end_time);


        }
    }
}