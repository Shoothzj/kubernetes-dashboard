/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import * as React from 'react';
import { Route, Routes, useNavigate } from 'react-router-dom';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import { AppBar, colors, Drawer } from '@mui/material';
import HomePage from './HomePage';
import DeploymentPage from './deployment/Deployment';
import NodePage from './node/node';
import StatefulSetPage from './statefulset/StatefulSet';
import SecretePage from './secrete/Secret';
import PodPage from './pod/Pod';
import JobPage from './job/Job';

const drawerWidth = 240;

function App() {
  const navigate = useNavigate();
  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        sx={{ width: `calc(100% - ${drawerWidth}px)`, ml: `${drawerWidth}px` }}>
        <Toolbar>
          <Typography variant="h6" noWrap component="div">
            Kubernetes dashboard
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
          },
        }}
        variant="permanent"
        anchor="left">
        <Toolbar />
        <Divider />
        <List>
          <ListItem key="job" disablePadding>
            <ListItemButton
              onClick={async () => {
                navigate('/job');
              }}>
              <ListItemText primary="job" />
            </ListItemButton>
          </ListItem>
          <ListItem key="pod" disablePadding>
            <ListItemButton
              onClick={async () => {
                navigate('/pod');
              }}>
              <ListItemText primary="pod" />
            </ListItemButton>
          </ListItem>
          <ListItem key="node" disablePadding>
            <ListItemButton
              onClick={async () => {
                navigate('/node');
              }}>
              <ListItemText primary="node" />
            </ListItemButton>
          </ListItem>
          <ListItem key="secret" disablePadding>
            <ListItemButton
              onClick={async () => {
                navigate('/secret');
              }}>
              <ListItemText primary="secret" />
            </ListItemButton>
          </ListItem>
          <ListItem key="statefulset" disablePadding>
            <ListItemButton
              onClick={async () => {
                navigate('/statefulset');
              }}>
              <ListItemText primary="statefulset" />
            </ListItemButton>
          </ListItem>
          <ListItem key="deployment" disablePadding>
            <ListItemButton
              onClick={async () => {
                navigate('/deployment');
              }}>
              <ListItemText primary="deployment" />
            </ListItemButton>
          </ListItem>
        </List>
      </Drawer>
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Toolbar />
        <div>
          <Routes>
            <Route path="/pod" element={<PodPage />} />
            <Route path="/job" element={<JobPage />} />
            <Route path="/node" element={<NodePage />} />
            <Route path="/secret" element={<SecretePage />} />
            <Route path="/deployment" element={<DeploymentPage />} />
            <Route path="/statefulset" element={<StatefulSetPage />} />
          </Routes>
        </div>
      </Box>
    </Box>
  );
}

export default App;
