package com.iomirea;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//временные адаптер и класс для сообщений, пока не существует конечного класса Message
class TempMessage {
    private String text;
    private boolean belongsToCurrentUser;

    public TempMessage(String text, boolean belongsToCurrentUser) {
        this.text = text;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String newText) {
        text = newText;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}

public class TempMessageAdapter extends BaseAdapter {

    List<TempMessage> messages = new ArrayList<TempMessage>();
    Context context;

    public TempMessageAdapter(Context context) {
        this.context = context;
    }

    public void add(TempMessage message){
        this.messages.add(message);
        notifyDataSetChanged();
    }

    public String getTextFromMessage(int position){
        return this.messages.get(position).getText();
    }

    public void editTextInMessage(int position, String text){
        this.messages.get(position).setText(text);
        notifyDataSetChanged();

        Toast.makeText(context, context.getResources().getString(R.string.message_editing_success), Toast.LENGTH_SHORT).show();
    }

    public void deleteTextInMessage(int position){
        this.messages.remove(position);
        notifyDataSetChanged();
    }

    public boolean belongToCurrentUser(int position){return this.messages.get(position).isBelongsToCurrentUser();}

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TempMessage message = messages.get(position);

        if (message.isBelongsToCurrentUser()) {
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getText());
        }
        else {
            convertView = messageInflater.inflate(R.layout.their_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getText());
        }
        return convertView;
    }

    class MessageViewHolder{
        public TextView messageBody;
    }
}
