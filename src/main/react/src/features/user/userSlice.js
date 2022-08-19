import {createAsyncThunk, createEntityAdapter, createSlice} from "@reduxjs/toolkit";
import {client} from "../../api/api";

const userAdapter = createEntityAdapter()

const initialState = userAdapter.getInitialState()

export const fetchUser = createAsyncThunk('user/fetchUser', async () => {
    const response = await client.get('http://localhost:8080/api/v1/users')
    return response.data
})

const userSlice = createSlice({
    name: 'user',
    initialState,
    extraReducers(builder) {
        builder.addCase(fetchUser.fulfilled, userAdapter.setOne)
    }
})

export default userSlice.reducer

export const { selectAll: selectAllUsers } =
userAdapter.getSelectors(state => state.user)