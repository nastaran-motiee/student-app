package com.example.studentsapp;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.studentsapp.model.Model;
import com.example.studentsapp.model.Student;
import com.example.studentsapp.ui.alerts.MissigNameAlertDialog;
import com.example.studentsapp.ui.alerts.MissingIdAlertDialog;

import java.util.List;


public class EditStudentDetailsFragment extends Fragment {
    View view;
    List<Student> data;
    String studentId;
    Button cancelButton;
    Button saveButton;
    EditText name;
    EditText id;
    EditText phone;
    EditText address;
    CheckBox checkBox;
    MissigNameAlertDialog missigNameAlertDialog;
    MissingIdAlertDialog missingIdAlertDialog;
    Button deleteButton;
    Student student;
    ProgressBar progressBar;


    public EditStudentDetailsFragment() {
        // Required empty public constructor
    }

    public static EditStudentDetailsFragment newInstance(String param1, String param2) {
        EditStudentDetailsFragment fragment = new EditStudentDetailsFragment();
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
        view = inflater.inflate(R.layout.fragment_edit_student_details, container, false);

        studentId= EditStudentDetailsFragmentArgs.fromBundle(getArguments()).getStudentId();
        Log.d("Tag1",studentId);
        name = ((EditText) view.findViewById(R.id.edit_name_et));
        id = ((EditText) view.findViewById(R.id.edit_id_et));
        phone = ((EditText) view.findViewById(R.id.edit_phone_et));
        address = ((EditText) view.findViewById(R.id.edit_address_et));
        checkBox = ((CheckBox) view.findViewById(R.id.edit_student_cb));
        progressBar = view.findViewById(R.id.edit_student_details_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        Model.instance.getStudentById(studentId, (student)->{
            this.student = student;
            checkBox.setChecked(student.cb);
            phone.setText(student.phone);
            id.setText(student.id);
            name.setText(student.name);
            address.setText(student.address);
            progressBar.setVisibility(View.GONE);

        });



        cancelButton = view.findViewById(R.id.edit_student_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack(R.id.fragment_student_details,false);

            }
        });



        saveButton = view.findViewById(R.id.edit_student_save_button);

        missigNameAlertDialog = new MissigNameAlertDialog();
        missingIdAlertDialog = new MissingIdAlertDialog();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Tag2",""+studentId);
                String st_name= ((EditText)view.findViewById(R.id.edit_name_et)).getText().toString();
                String st_id = ((EditText)view.findViewById(R.id.edit_id_et)).getText().toString();
                String st_phone = ((EditText)view.findViewById(R.id.edit_phone_et)).getText().toString();
                String st_address = ((EditText)view.findViewById(R.id.edit_address_et)).getText().toString();
                boolean st_cb = ((CheckBox)view.findViewById(R.id.edit_student_cb)).isChecked();
                Log.d("Tag2",""+st_id);




                if(name.equals("")){
                    missigNameAlertDialog.show(getParentFragmentManager(),"MissingNameAlertDialog");
                    return;
                }else if(id.equals("")){
                    missingIdAlertDialog.show(getParentFragmentManager(),"MissingIdAlertDialog");
                    return;
                }


                Model.instance.updateId(studentId,st_id,()->{
                    Model.instance.updateStudent(new Student(st_name,st_id,st_phone,st_address,st_cb));
                    Navigation.findNavController(view).popBackStack(R.id.fragment_students_list,false);
                });


            }
        });


        deleteButton = view.findViewById(R.id.edit_student_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.deleteStudent(student);
                Navigation.findNavController(view).popBackStack(R.id.fragment_students_list,false);
            }
        });
        return view;
    }

}