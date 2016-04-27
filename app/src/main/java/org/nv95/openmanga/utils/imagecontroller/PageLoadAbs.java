package org.nv95.openmanga.utils.imagecontroller;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import org.nv95.openmanga.items.MangaPage;
import org.nv95.openmanga.providers.MangaProvider;

public abstract class PageLoadAbs implements ImageLoadingListener, ImageLoadingProgressListener {
    protected final MangaPage page;
    private PageImageAvare view;

    public PageLoadAbs(MangaPage page, SubsamplingScaleImageView view) {
        this.page = page;
        this.view = new PageImageAvare(view);
    }

    protected abstract void preLoad();

    protected abstract void onLoadingComplete();

//    private class PrepareTask extends AsyncTask<Void,Void,String> {
//
//        @Override
//        protected String doInBackground(Void... params) {
//            try {
//                return ((MangaProvider) page.provider.newInstance()).getPageImage(page);
//            } catch (Exception e) {
//                return page.path;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String path) {
//            super.onPostExecute(path);
//            if (path != null) {
//                if (path.startsWith("/")) {
//                    path = "file://" + path;
//                }
//                ImageLoader.getInstance().displayImage(path, view, null, PageLoadAbs.this, PageLoadAbs.this);
//            } else {
//                onLoadingFailed(null, null, null);
//            }
//        }
//    }
//
//    public void load() {
//        preLoad();
//        new PrepareTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//    }

    public void load() {
        preLoad();
        String path;
        try {
            path = ((MangaProvider) page.provider.newInstance()).getPageImage(page);
        } catch (Exception e) {
            path = page.path;
        }
        if (path != null) {
            if (path.startsWith("/")) {
                path = "file://" + path;
            }
            ImageLoader.getInstance().displayImage(path, view, null, this, this);
        } else {
            onLoadingFailed(null, null, null);
        }

    }

    public void cancel(){
        ImageLoader.getInstance().cancelDisplayTask(this.view);
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {

    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        onLoadingComplete();
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {

    }

    @Override
    public void onProgressUpdate(String imageUri, View view, int current, int total) {

    }
}