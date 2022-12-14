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
import { useEffect, useState } from 'react';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import Box from '@mui/material/Box';
import BACKEND_HOST from '../Const';

function DeploymentPage() {
  const [deployments, setDeployments] = useState([]);

  const columns = [
    { field: 'deployName', headerName: 'deployName', width: 200 },
    { field: 'namespace', headerName: 'namespace', width: 200 },
    { field: 'replicas', headerName: 'replicas', width: 200 },
    { field: 'availableReplicas', headerName: 'availableReplicas', width: 200 },
    { field: 'creationTimestamp', headerName: 'creationTimestamp', width: 200 },
  ];

  const fetchDeployments = async () => {
    const resp = await fetch(`${BACKEND_HOST}/api/kubernetes/namespace/default/deployments`);
    const data = await resp.json();
    setDeployments(
      data.map((aux) => ({
        id: aux.deployName,
        namespace: aux.namespace,
        deployName: aux.deployName,
        replicas: aux.replicas,
        availableReplicas: aux.availableReplicas,
        creationTimestamp: aux.creationTimestamp,
      })),
    );
  };

  // 钩子
  useEffect(() => {
    fetchDeployments();
  }, []);

  return (
    <div>
      <h1>deployments</h1>
      <Box sx={{ height: 400, width: '100%' }}>
        <DataGrid
          rows={deployments}
          columns={columns}
          pageSize={10}
          rowsPerPageOptions={[10]}
          checkboxSelection
          disableSelectionOnClick
          experimentalFeatures={{ newEditingApi: true }}
          components={{ Toolbar: GridToolbar }}
        />
      </Box>
    </div>
  );
}

export default DeploymentPage;
