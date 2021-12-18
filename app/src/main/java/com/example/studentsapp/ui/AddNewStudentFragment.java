package com.example.studentsapp.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.studentsapp.R;
import com.example.studentsapp.model.Model;
import com.example.studentsapp.model.Student;
import com.example.studentsapp.ui.alerts.MissigNameAlertDialog;
import com.example.studentsapp.ui.alerts.MissingIdAlertDialog;


public class AddNewStudentFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    View view;
    ImageView selectImage;
    Button cancelButton;
    Button saveButton;
    String name;
    String id;
    String phone;
    String address;
    boolean cb;
    ProgressBar progressBar;

    MissigNameAlertDialog missigNameAlertDialog;
    MissingIdAlertDialog missingIdAlertDialog;
    public AddNewStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_new_student, container, false);
        selectImage = view.findViewById(R.id.add_student_image_view);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });



        cancelButton = view.findViewById(R.id.add_student_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack(R.id.fragment_student_details,false);

            }
        });

        saveButton = view.findViewById(R.id.add_student_save_button);
        missigNameAlertDialog = new MissigNameAlertDialog();
        missingIdAlertDialog = new MissingIdAlertDialog();
        progressBar = view.findViewById(R.id.add_new_student_progress_bar);
        progressBar.setVisibility(View.GONE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                saveButton.setEnabled(false);
                cancelButton.setEnabled(false);
                name= ((EditText)view.findViewById(R.id.edit_name_et)).getText().toString();
                id = ((EditText)view.findViewById(R.id.edit_id_et)).getText().toString();
                phone = ((EditText)view.findViewById(R.id.edit_phone_et)).getText().toString();
                address = ((EditText)view.findViewById(R.id.edit_address_et)).getText().toString();
                cb = ((CheckBox)view.findViewById(R.id.edit_student_cb)).isChecked();




                if(name.equals("")){
                    missigNameAlertDialog.show(getParentFragmentManager(),"MissingNameAlertDialog");
                    return;
                }else if(id.equals("")){
                    missingIdAlertDialog.show(getParentFragmentManager(),"MissingIdAlertDialog");
                    return;
                }

                Model.instance.addNewStudent(new Student(name,id,phone,address,cb,selectImage),()->{
                   Navigation.findNavController(view).popBackStack(R.id.fragment_students_list,false);

                });
            }
        });



        return view;

    }

    // GetContent creates an ActivityResultLauncher<String> to allow you to pass
// in the mime type you'd like to allow the user to select
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    selectImage.setImageURI(uri);


                }
            });

}