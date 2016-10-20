package com.tistory.massivcode.baseadapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Copyright 2016 Pureum Choe
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * <p>
 * Created by prChoe on 2016-10-17.
 */

public abstract class BaseRecyclerAdapter<Item, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClicked(View view, int position);
    }

    public interface OnRecyclerItemLongClickListener {
        void onRecyclerItemLongClicked(View view, int position);
    }

    /**
     * CONTENTS_ONLY : Header 와 Footer 없이 Contents 로만 이루어져 있음
     * HEADER : Header 와 Contents 로 이루어져 있음
     */
    public enum ViewType {
        CONTENTS_ONLY, HEADER, FOOTER, HEADER_FOOTER
    }

    public class Header {

    }

    public class Footer {

    }

    private List<Item> mData;

    public BaseRecyclerAdapter(@Nullable List<Item> mData) {
        this.mData = mData;
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        if (mData == null || mData.isEmpty()) {
            return 0;
        }
        return mData.size();
    }

    /**
     * 해당 포지션의 VH 을 리턴합니다. DateSet 이 null 일 경우 null 을 리턴합니다.
     *
     * @param position : VH 의 포지션
     * @return : 해당 포지션의 VH
     */
    public Item getItem(int position) {
        if (mData == null) {
            return null;
        }

        return mData.get(position);
    }

    /**
     * 기존 DataSet 를 Clear 하고 새로운 DateSet 으로 데이터를 교체합니다.
     *
     * @param items : 갈아치워질 새로운 DataSet
     */
    public void swapItems(List<Item> items) {
        if (mData == null) {
            mData = items;
            notifyDataSetChanged();
            return;
        }

        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 기존 DataSet 의 마지막에 새 item 을 삽입합니다.
     *
     * @param item : DataSet 에 삽입할 item
     */
    public void insertItem(Item item) {
        if (item == null) {
            return;
        }

        if (mData == null) {
            return;
        }

        mData.add(item);
        notifyItemInserted(mData.size() - 1);
    }

    /**
     * 지정된 position 에 새 item 을 삽입합니다.
     *
     * @param item     : DateSet 에 삽입할 item
     * @param position : 삽입할 DataSet 의 Position
     */
    public void insertItem(Item item, int position) {
        if (item == null) {
            return;
        }

        if (mData == null) {
            return;
        }

        mData.add(position, item);
        notifyDataSetChanged();
    }

    /**
     * 기존 DataSet 에서 item 과 동일한 item 을 제거합니다.
     *
     * @param item : 제거할 item
     * @return : 제거에 성공했으면 true, 기존 DataSet 에 포함되지 않아 실패했으면 false
     */
    public boolean removeItem(Item item) {
        boolean isRemoved = false;

        if (mData != null) {
            int removePosition = mData.indexOf(item);
            isRemoved = mData.remove(item);

            if (removePosition != -1) {
                notifyItemRemoved(removePosition);
            }
        }

        return isRemoved;
    }

    /**
     * 기존 DataSet 에서 position 에 위치한 VH 을 제거합니다.
     *
     * @param position : 제거할 item 의 position
     * @return
     */
    public boolean removeItem(int position) {
        boolean isRemoved = false;

        if (mData != null) {
            mData.remove(position);
            isRemoved = true;
            notifyItemRemoved(position);
        }

        return isRemoved;
    }

    public void updateItem(int position, Item newItem) {
        if (mData == null) {
            return;
        }

        mData.set(position, newItem);
        notifyItemChanged(position);
    }

    public abstract void setOnItemClickListener(OnRecyclerItemClickListener listener);

    public abstract void setOnItemLongClickListener(OnRecyclerItemLongClickListener listener);


}
