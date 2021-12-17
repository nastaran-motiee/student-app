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
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data = AppLocalDb.db.studentDao().getAll();
            MyApplication.mainHandler.post(()->{
                listener.onComplete(data);
            });
        });
    }

    public void addNewStudent(Student student){
        MyApplication.executorService.execute(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AppLocalDb.db.studentDao().insertAll(student);
        });
    }

    public void updateStudent(Student student){
        MyApplication.executorService.execute(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AppLocalDb.db.studentDao().updateStudent(student);
        });
    }

    public interface GetStudentByIdListener{
        void onCompelete(Student student);
    }

    public void getStudentById(String studentId,GetStudentByIdListener listener) {
        MyApplication.executorService.execute(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
