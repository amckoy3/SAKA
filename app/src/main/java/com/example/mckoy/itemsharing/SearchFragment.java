package com.example.mckoy.itemsharing;
/*
Referenced portions of code from project facilitator Katie Dektar, to get the search function working
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.example.mckoy.itemsharing.ListOfItemsActivity.mQueryKey;

public class SearchFragment extends Fragment {
    private SearchItemAdapter mAdapter;
    private TextView mTextField;
    private ListView mListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.search_frag,container,false);
        mListView = (ListView)view.findViewById(R.id.list_view);
        mTextField = (TextView)view.findViewById(R.id.resultsList);
        mAdapter = new SearchItemAdapter(getActivity());

        String result = getArguments().getString(mQueryKey);
        mTextField.append(result);


        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = mListView.getItemAtPosition(position);
                Item item = (Item)o;
                //Toast.makeText(ListOfItemsActivity.this, "You have chosen : " + " " + item.getItemName(), Toast.LENGTH_LONG).show();

                //this will call ItemDetailsActivity
                Intent i = new Intent(mListView.getContext(), ItemDetailsActivity.class);
                i.putExtra("itemObject", item);
                startActivity(i);
            }
        });
        ItemDataSource.get(getContext()).getItems(result,new ItemDataSource.ItemListener(){
            public void onItemsReceived(List<Item>listofItems){
                mAdapter.setItems(listofItems);
            }
        });
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putString("text", mTextField.getText().toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private class SearchItemAdapter extends BaseAdapter {
        private Context conText;
        private LayoutInflater inFlater;
        private List<Item> data;

        public SearchItemAdapter(Context ctxt){
            data = new ArrayList<>();
            conText = ctxt;
            inFlater = (LayoutInflater)ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setItems(List<Item>items){
            data.clear();
            data.addAll(items);
            notifyDataSetChanged();
        }
        public int getCount(){
            return data.size();
        }

        public Object getItem(int index){
            return data.get(index);

        }
        public long getItemId(int index){
            return index;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = inFlater.inflate(R.layout.list_view_item, parent, false);
            final Item itemResult = data.get(position);
            TextView sellerName = (TextView) view.findViewById(R.id.sellerName);
            TextView itemName = (TextView) view.findViewById(R.id.itemName);
            TextView itemPrice = (TextView) view.findViewById(R.id.price_of_item);
            final ImageView itemImage = (ImageView) view.findViewById(R.id.itemImage);


            String url = itemResult.getPhotourl();
            Glide.with(itemImage.getContext()).load(url).into(itemImage);




            //NetworkImageView thumbImg = (NetworkImageView)view.findViewById(R.id.thumbNail);


            sellerName.setText(itemResult.getSellerName());
            itemName.setText(itemResult.getItemName());
            itemPrice.setText("Price: $" + itemResult.getPrice());

           /* if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_view_item, parent, false);
        }

        TextView sellerName = (TextView) convertView.findViewById(R.id.sellerName);
        TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
        TextView itemPrice = (TextView) convertView.findViewById(R.id.price_of_item);
        Item item = getItem(position);
        //declare how to show the image in 'itemImage' ImageView
        sellerName.setText(item.getSellerName());
        itemName.setText(item.getItemName());
        itemPrice.setText("Price: $" + item.getPrice());

        return convertView;*/

            return view;




        }
    }





}

