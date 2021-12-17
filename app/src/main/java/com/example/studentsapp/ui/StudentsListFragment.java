package com.example.studentsapp.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studentsapp.NavGraphDirections;
import com.example.studentsapp.R;
import com.example.studentsapp.StudentDetailsFragmentDirections;
import com.example.studentsapp.model.Model;
import com.example.studentsapp.model.Student;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;


public class StudentsListFragment extends Fragment {
    View view;
    LinearLayoutManager layoutManager;
    static List<Student> data = new LinkedList<>();
    String studentImage;
    MyAdapter adapter;
    ProgressBar progressBar;



    public StudentsListFragment() {
        // Required empty public constructor
    }


    public static StudentsListFragment newInstance(String param1, String param2) {
        StudentsListFragment fragment = new StudentsListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_students_list, container, false);
        //----------------------------------

        RecyclerView studentsList = view.findViewById(R.id.students_list_rv);
        studentsList.setHasFixedSize(true);

        //use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        studentsList.setLayoutManager(layoutManager);
        //----------------------------------
        setHasOptionsMenu(true);

        progressBar.setVisibility(view.VISIBLE);

        Model.instance.getStudentList(new Model.getStudentListListener() {
            @Override
            public void onComplete(List<Student> d) {
                data = d;
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new MyAdapter();
        studentsList.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position,View v) {
                Student st = data.get(position);
                NavDirections action = StudentsListFragmentDirections.actionGlobalStudentDetailsFragment(st.getId());
                Navigation.findNavController(view).navigate(action);
                Log.d("TAG", "row was clicked " + data.get(position).id);
            }
        });
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private final OnItemClickListener listener;
        TextView nameTv;
        TextView idTv;
        CheckBox cb;
        ImageView image;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.list_row_name_text_view);
            idTv = itemView.findViewById(R.id.list_row_id_text_view);
            cb = itemView.findViewById(R.id.list_row_cb);
            image = itemView.findViewById(R.id.list_row_avatar);
            this.listener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(pos,v);
                    }

                }
            });
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    data.get(pos).cb = cb.isChecked();


                }
            });
        }

        public void bind(Student student) {
            nameTv.setText(student.name);
            idTv.setText(student.id);
            cb.setChecked(student.cb);


        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position,View v);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private OnItemClickListener listener;

        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View rowView = inflater.inflate(R.layout.student_list_row,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(rowView, listener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Student student = data.get(position);
            holder.bind(student);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }


    }


