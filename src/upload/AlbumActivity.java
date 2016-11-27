package upload;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tools.Exit;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AlbumActivity extends BaseActivity {

    private GridView gridView;
    private ArrayList<String> dataList = new ArrayList<String>();
    private HashMap<String, ImageView> hashMap = new HashMap<String, ImageView>();
    // private String cameraDir = "/DCIM/";
    private ProgressBar progressBar;
    private AlbumGridViewAdapter gridImageAdapter;
    private LinearLayout selectedImageLayout;
    private TextView okButton;
    private HorizontalScrollView scrollview;

    private PhotoAibum aibum;

    
    /* (non-Javadoc)
     * @see com.mkcomingc.BaseActivity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    /* (non-Javadoc)
     * @see com.mkcomingc.BaseActivity#onPause()
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.photoselect_album_detail_activity);
	Exit.getInstance().addActivity(this);
	Intent intent = getIntent();
	Bundle bundle = intent.getExtras();
	aibum = (PhotoAibum) getIntent().getExtras().get("aibum");

	init();
	initListener();

    }

    private void init() {

	progressBar = (ProgressBar) findViewById(R.id.progressbar);
	progressBar.setVisibility(View.GONE);
	gridView = (GridView) findViewById(R.id.myGrid);
	gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
		PhotoAlbumActivity.selectedDataList);
	gridView.setAdapter(gridImageAdapter);
	refreshData();
	selectedImageLayout = (LinearLayout) findViewById(R.id.selected_image_layout);
	okButton = (TextView) findViewById(R.id.ok_button);
	scrollview = (HorizontalScrollView) findViewById(R.id.scrollview);

	initSelectImage();

    }

    private void initSelectImage() {
	if (PhotoAlbumActivity.selectedDataList == null)
	    return;
	for (final String path : PhotoAlbumActivity.selectedDataList) {
	    ImageView imageView = (ImageView) LayoutInflater.from(
		    AlbumActivity.this).inflate(
		    R.layout.photoselect_album_choose_item,
		    selectedImageLayout, false);
	    selectedImageLayout.addView(imageView);
	    hashMap.put(path, imageView);
	    ImageManager2.from(AlbumActivity.this).displayImage(imageView,
		    path, R.drawable.photoselect_bg, 400, 400);
	    imageView.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
		    removePath(path);
		    gridImageAdapter.notifyDataSetChanged();
		}
	    });
	}
	okButton.setText("���(" + PhotoAlbumActivity.selectedDataList.size()
		+ "/4)");
    }

    private void initListener() {

	gridImageAdapter
		.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

		    @Override
		    public void onItemClick(final ImageView photo_select,
			    final ToggleButton toggleButton, int position,
			    final String path, boolean isChecked) {

			if (PhotoAlbumActivity.selectedDataList.size() >= 4) {
			    photo_select
				    .setImageResource(R.drawable.photoselect_pic_unsel);
			    toggleButton.setChecked(false);
			    if (!removePath(path)) {
				Toast.makeText(AlbumActivity.this, "ֻ��ѡ��4��ͼƬ",
					200).show();
			    }
			    return;
			}

			if (isChecked) {
			    if (!hashMap.containsKey(path)) {
				ImageView imageView = (ImageView) LayoutInflater
					.from(AlbumActivity.this)
					.inflate(
						R.layout.photoselect_album_choose_item,
						selectedImageLayout, false);
				selectedImageLayout.addView(imageView);
				imageView.postDelayed(new Runnable() {

				    @Override
				    public void run() {

					int off = selectedImageLayout
						.getMeasuredWidth()
						- scrollview.getWidth();
					if (off > 0) {
					    scrollview.smoothScrollTo(off, 0);
					}

				    }
				}, 100);

				hashMap.put(path, imageView);
				PhotoAlbumActivity.selectedDataList.add(path);
				ImageManager2.from(AlbumActivity.this)
					.displayImage(imageView, path,
						R.drawable.photoselect_bg, 400,
						400);
				imageView
					.setOnClickListener(new View.OnClickListener() {

					    @Override
					    public void onClick(View v) {
						toggleButton.setChecked(false);
						photo_select
							.setImageResource(R.drawable.photoselect_pic_unsel);
						removePath(path);

					    }
					});
				okButton.setText("���("
					+ PhotoAlbumActivity.selectedDataList
						.size() + "/4)");
			    }
			} else {
			    removePath(path);
			}

		    }
		});

	okButton.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {

		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		// intent.putArrayListExtra("dataList", dataList);
		bundle.putStringArrayList("dataList",
			PhotoAlbumActivity.selectedDataList);
		intent.putExtras(bundle);
		setResult(RESULT_OK, intent);
		finish();

	    }
	});

    }

    private boolean removePath(String path) {
	if (hashMap.containsKey(path)) {
	    selectedImageLayout.removeView(hashMap.get(path));
	    hashMap.remove(path);
	    removeOneData(PhotoAlbumActivity.selectedDataList, path);
	    okButton.setText("���(" + PhotoAlbumActivity.selectedDataList.size()
		    + "/4)");
	    return true;
	} else {
	    return false;
	}
    }

    private void removeOneData(ArrayList<String> arrayList, String s) {
	for (int i = 0; i < arrayList.size(); i++) {
	    if (arrayList.get(i).equals(s)) {
		arrayList.remove(i);
		return;
	    }
	}
    }

    private void refreshData() {

	new AsyncTask<Void, Void, ArrayList<String>>() {

	    @Override
	    protected void onPreExecute() {
		progressBar.setVisibility(View.VISIBLE);
		super.onPreExecute();
	    }

	    @Override
	    protected ArrayList<String> doInBackground(Void... params) {
		// ArrayList<String> tmpList = new ArrayList<String>();
		// ArrayList<String> listDirlocal = listAlldir( new
		// File(cameraDir));
		// ArrayList<String> listDiranjuke = new ArrayList<String>();
		// listDiranjuke.addAll(listDirlocal);

		// for (int i = 0; i < listDiranjuke.size(); i++){
		// listAllfile( new File( listDiranjuke.get(i) ),tmpList);
		// }
		ArrayList<String> tmpList = getList(aibum);
		return tmpList;
	    }

	    private ArrayList<String> getList(PhotoAibum aibum) {
		ArrayList<String> photospath = new ArrayList<String>();
		List<PhotoItem> photoItems = aibum.getBitList();
		int size = photoItems.size();
		for (int i = 0; i < size; i++) {
		    photospath.add(photoItems.get(i).getPath());
		}
		return photospath;
	    }

	    protected void onPostExecute(ArrayList<String> tmpList) {

		if (AlbumActivity.this == null
			|| AlbumActivity.this.isFinishing()) {
		    return;
		}
		progressBar.setVisibility(View.GONE);
		dataList.clear();
		dataList.addAll(tmpList);
		gridImageAdapter.notifyDataSetChanged();
		return;

	    };

	}.execute();

    }

    private ArrayList<String> listAlldir(File nowDir) {
	ArrayList<String> listDir = new ArrayList<String>();
	nowDir = new File(Environment.getExternalStorageDirectory()
		+ nowDir.getPath());
	if (!nowDir.isDirectory()) {
	    return listDir;
	}

	File[] files = nowDir.listFiles();

	for (int i = 0; i < files.length; i++) {
	    if (files[i].getName().substring(0, 1).equals(".")) {
		continue;
	    }
	    File file = new File(files[i].getPath());
	    if (file.isDirectory()) {
		listDir.add(files[i].getPath());
	    }
	}
	return listDir;
    }

    private ArrayList<String> listAllfile(File baseFile,
	    ArrayList<String> tmpList) {
	if (baseFile != null && baseFile.exists()) {
	    File[] file = baseFile.listFiles();
	    for (File f : file) {
		if (f.getPath().endsWith(".jpg")
			|| f.getPath().endsWith(".png")) {
		    tmpList.add(f.getPath());
		}
	    }
	}
	return tmpList;
    }

    @Override
    public void onBackPressed() {
	finish();
	// super.onBackPressed();
    }

    @Override
    public void finish() {
	// TODO Auto-generated method stub
	super.finish();
	// ImageManager2.from(AlbumActivity.this).recycle(dataList);
    }

    @Override
    protected void onDestroy() {

	super.onDestroy();
    }

}
