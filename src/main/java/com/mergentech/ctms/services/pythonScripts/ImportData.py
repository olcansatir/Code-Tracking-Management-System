# -*- coding: utf-8 -*-
"""
Created on Fri Nov 12 17:16:11 2021
@author: nedim
"""

from perceval.backends.core.git import Git
from perceval.backends.core.gitlab import GitLab
from collections import Counter
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import argparse
import json

#dic_GITLAB={str:{"category":"a",}}
dic_GITLAB={}
dic_GIT={}

def extract_gitlab_identities():
    pull_request_users=[]
    issue_users=[]
    out = ""
    repo=GitLab(owner="nedim_ozhan_eyupreisoglu",repository="test",api_token="glpat-ygftM8ECFTFCoY8ysHWf",sleep_for_rate=True)

    for issue in repo.fetch():
        #data.append(issue["origin"])
        #print(issue["data"])
        author_id=issue["data"]["author"]["id"]
        #print(issue["data"]["closed_by"]["web_url"])
        issue["data"]["author"].pop('web_url')
        issue["data"]["author"].pop('avatar_url')

        
        if author_id in dic_GITLAB :
            dic_GITLAB[author_id]["category"].append(issue["category"])
            dic_GITLAB[author_id]["searchfields"].append(issue["search_fields"])
            dic_GITLAB[author_id]["tag"].append(issue["tag"])
            dic_GITLAB[author_id]["data"].append(issue["data"])           
        else:
            dic_GITLAB[author_id]={}
            dic_GITLAB[author_id]["category"]=[]
            dic_GITLAB[author_id]["category"].append(issue["category"])
            dic_GITLAB[author_id]["searchfields"]=[]
            dic_GITLAB[author_id]["searchfields"].append(issue["search_fields"])
            dic_GITLAB[author_id]["tag"]=[]
            dic_GITLAB[author_id]["tag"].append(issue["tag"])
            dic_GITLAB[author_id]["data"]=[]
            dic_GITLAB[author_id]["data"].append(issue["data"])
        print(issue)
    #print(dic_GITLAB)
            
            
        # login=issue["data"]["user_data"]["login"]
        # email=issue["data"]["user_data"]["email"]
        # data.append(issue["data"])

        # if not email:
        #     email=""
        # if "pull_request" in issue["data"]:
        #     pull_request_users.append(login+ " " + email)
        # else:
        #     issue_users.append(login+ " " + email)
       
    #df = pd.DataFrame (pull_request_users["data"], columns = ['column_name'])
    
    #df = pd.DataFrame(data)
    #np.savetxt(r'deneme.txt', df.values, fmt='%s')
    #print(df)
    
    # print(df["title"]["comments"]["created_at"]["updated_at"]["closed_at"]) 
    # print("issues :" + str(Counter(issue_users).get(u))+"\t user: "+u)   
    # print("issues :" + str(Counter(pull_request_users).get(u))+"\t user: "+u)
    # print(data)

def deneme():    
    
#       #url for the git repo to analyze
        repo_url = "https://gitlab.com/nedim_ozhan_eyupreisoglu/test"
#       #directory for letting Perceval clone the git repo
        
# #      #create a Git object, pointing to repo_url, using repo_dir for cloning
        repo = Git(uri=repo_url,gitpath="clonedRepo")
# #      #fetch all commits as an iterator, and iterate it printing each hash
        for commit in repo.fetch():
#           print(commit['data']['commit'])
            author_id=commit["data"]["author"]
            
            if author_id in dic_GITLAB :
                dic_GIT[author_id]["origin"].append(commit["origin"])
                dic_GIT[author_id]["search_fields"].append(commit["search_fields"])
                dic_GIT[author_id]["tag"].append(commit["tag"])
                dic_GIT[author_id]["category"].append(commit["category"])
                dic_GIT[author_id]["data"].append(commit["data"])           
            else:
                dic_GIT[author_id]={}
                dic_GIT[author_id]["origin"]=[]
                dic_GIT[author_id]["origin"].append(commit["origin"])
                dic_GIT[author_id]["category"]=[]
                dic_GIT[author_id]["category"].append(commit["category"])
                dic_GIT[author_id]["search_fields"]=[]
                dic_GIT[author_id]["search_fields"].append(commit["search_fields"])
                dic_GIT[author_id]["tag"].append(commit["tag"])
                dic_GIT[author_id]["data"]=[]
                dic_GIT[author_id]["data"].append(commit["data"])
            print(commit)
extract_gitlab_identities()
#deneme()
