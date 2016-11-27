/**
 * 
 */
package adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.alipay.a.a.l;
import com.mkcomingc.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import adapter.DesignerListAdapter.ViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * @author Liming Chu
 *
 * @param
 * @return
 */
public class GroupOrderListAdapter extends BaseAdapter{

    private ArrayList<HashMap<String, String>> list;
    private Context context;
    private LayoutInflater lInflater;
    private DisplayImageOptions options;
    
    
    public GroupOrderListAdapter(ArrayList<HashMap<String, String>> list,
	    Context context) {
	super();
	this.list = list;
	this.context = context;
	this.lInflater = LayoutInflater.from(context);
	preperImageLoader();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return list.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
	// TODO Auto-generated method stub
	return list.get(position);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
	// TODO Auto-generated method stub
	return position;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	ViewHolder vHolder = new ViewHolder();
	if (convertView == null) {
	    convertView = lInflater.inflate(R.layout.designer_list_item, null);
	}
	vHolder.name = (TextView) convertView
		.findViewById(R.id.designer_list_item_name);
	vHolder.sex = (ImageView) convertView
		.findViewById(R.id.designer_list_item_sex);
	vHolder.price = (TextView) convertView
		.findViewById(R.id.designer_list_item_price);
	vHolder.pic = (ImageView) convertView
		.findViewById(R.id.designer_list_item_pic);
	vHolder.level = (TextView) convertView
		.findViewById(R.id.designer_list_item_level);
	vHolder.count = (TextView) convertView
		.findViewById(R.id.designer_list_item_count);
	vHolder.rb = (RatingBar) convertView
		.findViewById(R.id.designer_list_item_ratingbar);
	vHolder.rate = (TextView) convertView
		.findViewById(R.id.designer_list_item_rate);
	vHolder.price.setVisibility(View.GONE);
	
	
	return convertView;
    }
    
    class ViewHolder {
	TextView name;
	ImageView sex;
	TextView price;
	ImageView pic;
	TextView level;
	RatingBar rb;
	TextView count;
	TextView rate;
    }

    private void preperImageLoader() {

	/******************* ����ImageLoder ***********************************************/
	File cacheDir = StorageUtils.getOwnCacheDirectory(context,
		"imageloader/Cache");

	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		context).denyCacheImageMultipleSizesInMemory()
		.discCache(new UnlimitedDiscCache(cacheDir))// �Զ��建��·��
		.build();// ��ʼ����

	options = new DisplayImageOptions.Builder().cacheInMemory()
		.cacheOnDisc().imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.showImageForEmptyUri(R.drawable.question)
		.showImageOnFail(R.drawable.question).build();

	ImageLoader.getInstance().init(config);// ȫ�ֳ�ʼ��������
	/*********************************************************************************/
    }
    
}
