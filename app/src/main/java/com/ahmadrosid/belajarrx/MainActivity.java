package com.ahmadrosid.belajarrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ahmadrosid.belajarrx.model.User;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private TextView tMain;
    private Button btn_do_subscribe;
    private RadioButton radio_basic;
    private RadioButton radio_map;
    private RadioButton radio_more_map;
    private RadioGroup radio_active;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tMain = (TextView) findViewById(R.id.tMain);
        btn_do_subscribe = (Button) findViewById(R.id.btn_do_subscribe);
        radio_basic = (RadioButton) findViewById(R.id.radio_basic);
        radio_map = (RadioButton) findViewById(R.id.radio_map);
        radio_more_map = (RadioButton) findViewById(R.id.radio_more_map);
        radio_active = (RadioGroup) findViewById(R.id.radio_active);

        final Observable<String> myObservable =
                Observable.just("Hello, world!");
        final Observable<Integer> moreObservable =
                Observable.just(1, 2, 3, 4, 5);

        user = new User("rosid", "ocittwo@gmail.com");
        final Observable<User> userObservable = Observable.just(user);

        btn_do_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = radio_active.getCheckedRadioButtonId();
                switch (id){
                    case R.id.radio_basic:
                        myObservable.subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                tMain.setText(s);
                            }
                        });
                        break;
                    case R.id.radio_map:
                        userObservable.map(new Func1<User, String>() {
                            @Override
                            public String call(User user) {
                                return "Nama : " + user.name + "\n" + "Email : " + user.email;
                            }
                        }).subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                tMain.setText(s);
                            }
                        });
                        break;
                    case R.id.radio_more_map:
                        final StringBuilder stringBuilder = new StringBuilder();
                        moreObservable.map(new Func1<Integer, Integer>() {
                            @Override
                            public Integer call(Integer integer) {
                                return integer + 1;
                            }
                        }).map(new Func1<Integer, String>() {
                            @Override
                            public String call(Integer integer) {
                                int origin = integer - 1;
                                stringBuilder.append("Angka " + origin + " di tambah 1 = ");
                                stringBuilder.append(integer + "\n");
                                return stringBuilder.toString();
                            }
                        }).subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                tMain.setText(s);
                            }
                        });
                        break;
                }

            }
        });
    }
}
