package com.bugull.android.ext.sam;

import android.os.Handler;
import android.view.KeyEvent;

import androidx.lifecycle.Observer;

import com.bugull.android.ext.methodhandle.MHJava;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.Predicate;

import javax.security.auth.callback.Callback;

public class AmazeSAMJava implements Observer<String>{

    @Test
    public void test01() {

        HelloInterface aNew = MHJava::new;
        aNew.hello();

       // Observer<String> callback = MHJava::new;

        AmazeSAMJava2 aNew02 = MHJava::new;
        aNew02.create();

    }

    @Override
    public void onChanged(String o) {

    }

    interface HelloInterface{
        void hello();
    }

}


