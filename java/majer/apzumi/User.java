package majer.apzumi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class User extends Activity{
    TextView user_name;
    TextView user_rep;
    TextView user_description;
    ImageView imageView_user;
    ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        user_name = findViewById(R.id.text_user_name);
        user_rep = findViewById(R.id.text_user_rep);
        user_description = findViewById(R.id.text_user_description);
        imageView_user = findViewById(R.id.imageView_user);
        buttonBack = findViewById(R.id.buttonBack);

        String user_intent_name = getIntent().getStringExtra("name");
        String user_intent_rep = getIntent().getStringExtra("rep");
        String user_intent_avatar = getIntent().getStringExtra("avatar");
        String user_intent_description = getIntent().getStringExtra("description");

        user_name.setText(user_intent_name);
        user_rep.setText(user_intent_rep);
        user_description.setText(user_intent_description);
        Picasso.get().load(user_intent_avatar).into(imageView_user);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
