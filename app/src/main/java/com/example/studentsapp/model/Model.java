package com.example.studentsapp.model;

import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class Model {

    private List<Student> data = new LinkedList<Student>();

    static final public Model instance = new Model();

    private Model(){

    }


    public interface getStudentListListener{
        void onComplete(List<Student> data);

    }

    public void getStudentList(getStudentListListener listener){

        MyApplication.executorService.execute(()->{
            data = AppLocalDb.db.studentDao().getAll();
            MyApplication.mainHandler.post(()->{
                listener.onComplete(data);
            });
        });
    }


    public interface AddStudentListener{
        void onComplete();
    }
    public void addNewStudent(Student student,AddStudentListener listener){
        MyApplication.executorService.execute(()->{
            AppLocalDb.db.studentDao().insertAll(student);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }


    public void updateStudent(Student student){
        MyApplication.executorService.execute(()-> {
            AppLocalDb.db.studentDao().updateStudent(student);

        });
    }

    public interface UpdateId{
        void onComplete();
    }

    public void updateId(String current_id, String updated_id,UpdateId listener){
        MyApplication.executorService.execute(()-> {
            AppLocalDb.db.studentDao().updateId(current_id, updated_id);
            MyApplication.mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }

    public void deleteStudent(Student student){
        MyApplication.executorService.execute(()->{
            AppLocalDb.db.studentDao().deleteStudent(student);
        });

    }
    public interface GetStudentByIdListener{
        void onCompelete(Student student);
    }

    public void getStudentById(String studentId,GetStudentByIdListener listener) {
        MyApplication.executorService.execute(()->{

            Student student = AppLocalDb.db.studentDao().getStudentById(studentId);
            MyApplication.mainHandler.post(()->{
                listener.onCompelete(student);
            });
        });

    }

  public Student getStById(String studentId){
      for(Student s_:data){
          if(s_.id.equals(studentId)){
              return s_;
          }
      }
      return null;
  }
}
