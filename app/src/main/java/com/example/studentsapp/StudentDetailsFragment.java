package com.example.studentsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studentsapp.model.Model;
import com.example.studentsapp.model.Student;

import java.util.List;

public class StudentDetailsFragment extends Fragment {
    View view;
    TextView name;
    TextView id;
    TextView phone;
    TextView address;
    CheckBox cb;
    List<Student> data;
    Button editBtn;
    Student student;
    ProgressBar progressBar;



    public StudentDetailsFragment() {
        // Required empty public constructor
    }


    public static StudentDetailsFragment newInstance(String param1, String param2) {
        StudentDetailsFragment fragment = new StudentDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_student_details, container, false);

        String studentId = StudentDetailsFragmentArgs.fromBundle(getArguments()).getStudentId();
        name = ((TextView) view.findViewById(R.id.student_details_name_et));
        id = ((TextView) view.findViewById(R.id.student_details_id_et));
        phone = ((TextView) view.findViewById(R.id.student_details_phone_et));
        address = ((TextView) view.findViewById(R.id.student_details_address_et));
        cb = ((CheckBox) view.findViewById(R.id.student_details_check_box));
        progressBar = view.findViewById(R.id.student_details_progress_bar);
        progressBar.setVisibility(view.VISIBLE);
        Model.instance.getStudentById(studentId, (student)->{
            updateDisplay(student);
        });

        if (student != null){
            updateDisplay(student);
        }
        editBtn = view.findViewById(R.id.student_details_Edit_Button);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = StudentDetailsFragmentDirections.actionStudentDetailsFragmentToEditStudentDetails(studentId);
                Navigation.findNavController(view).navigate(action);
            }
        });


        return view;

    }

    public void updateDisplay(Student student){
        this.student = student;
        cb.setChecked(student.cb);
        phone.setText(student.phone);
        id.setText(student.id);
        name.setText(student.name);
        address.setText(student.address);
        progressBar.setVisibility(View.GONE);


    }
}