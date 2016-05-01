package com.example.jobs;

import android.content.Context;
import android.util.Log;

import com.example.events.GetCategoryListEvent;
import com.example.hypermarketpurchase.MyApplication;
import com.example.models.CategoryListResponse;
import com.example.utils.Priority;
import com.example.utils.URLConst;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import de.greenrobot.event.EventBus;


public class GetCategoryListJob extends Job {

    private static final AtomicInteger jobCounter = new AtomicInteger(0);
    private final int id;

    public GetCategoryListJob() {
        super(new Params(Priority.HIGH).requireNetwork());
        id = jobCounter.incrementAndGet();
    }


    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {

        if (id != jobCounter.get()) {
            // looks like other fetch jobs has been added after me. no reason to
            // keep fetching many times, cancel me, let the other one fetch
            return;
        }

        Context context = MyApplication.getInstance();
        String url = URLConst.getCategoryList();

        Log.d("TAG", "url = " + url);

        Response<String> response = Ion.with(context)
                .load("GET", url)
                .setStringBody("")
                .asString()
                .withResponse()
                .get();

        String json = response.getResult();
        Log.d("TAG", "response = " + json);

        if (response.getHeaders().code() != HttpURLConnection.HTTP_OK) {
            EventBus.getDefault().post(new GetCategoryListEvent.Fail(
                    new Exception("Error code: " + response.getHeaders().code())));
            return;
        }

        ArrayList<CategoryListResponse> shippingAddressList = CategoryListResponse.toList(json);
        EventBus.getDefault().post(new GetCategoryListEvent.Success(shippingAddressList));
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        EventBus.getDefault().post(new GetCategoryListEvent.Fail(new Exception(throwable.getCause().toString())));
        return false;
    }
}
