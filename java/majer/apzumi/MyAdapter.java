package majer.apzumi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<RepositoryData> {

    ArrayList<RepositoryData> repositoryData;
    Context context;
    int resource;

    public MyAdapter(Context context, int resource, ArrayList<RepositoryData> repositoryData) {
        super(context, resource, repositoryData);
        this.repositoryData = repositoryData;
        this.context = context;
        this.resource = resource;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_list, null, true);
        }
        RepositoryData rd = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageAvatar);
        Picasso.get().load(rd.getImage()).into(imageView);

        TextView txtName = convertView.findViewById(R.id.text_name);
        txtName.setText(rd.getName());

        TextView txtRep = convertView.findViewById(R.id.text_rep);
        txtRep.setText(rd.getRep());

       if(txtRep.getText().equals("Bitbucket")) {
           convertView.setBackgroundResource(R.color.colorBitbucket);
       }
       else {
           convertView.setBackgroundResource(R.color.colorGithub);
       }

        return convertView;
    }
}

