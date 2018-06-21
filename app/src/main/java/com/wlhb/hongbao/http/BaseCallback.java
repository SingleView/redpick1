package com.wlhb.hongbao.http;

import android.util.Log;

import com.wlhb.hongbao.base.IViewController;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JS01 on 2016/6/14.
 */
public abstract class BaseCallback<T> implements Callback<T> {

    private static final String TAG = "BaseCallback";

    private WeakReference<IViewController> weakRef;

    public BaseCallback(Call<T> call, IViewController controller) {
        if (controller != null) {
            this.weakRef = new WeakReference<>(controller);
        }
        this.onStart();
        CallManager.add(call);
    }

    public BaseCallback(Call<T> call) {
        this(call, null);
    }



    public void onStart() {
        IViewController controller = null;
        if (weakRef != null) {
            controller = weakRef.get();
        }

        if (controller != null) {
            controller.showLoading();
        }
    }

    public abstract void onResponse(Response<T> response);

    public void onFailure(Throwable t) {
    }

    public void onFinish(boolean success) {
        Log.d(TAG, "success:" + success);
        IViewController controller = null;
        if (weakRef != null) {
            controller = weakRef.get();
        }
        if (controller != null) {
            controller.hideLoading();
        }
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.d(TAG, "code:" + response.code());
        if (response.isSuccessful()) {
            this.onResponse(response);
            this.onFinish(true);
        } else {
            this.onFailure(new Throwable(response.code() + ""));
            this.onFinish(false);
        }
        CallManager.remove(call);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d(TAG, "error:" + t.getLocalizedMessage());
        this.onFailure(t);
        this.onFinish(false);
        CallManager.remove(call);
    }

}
