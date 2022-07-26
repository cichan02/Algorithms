#include <fstream>
#include <vector>
#include <map>
#include <limits>
#include <queue>

using namespace std;

typedef long long int lli;
typedef pair<lli, int> pli;

int main() {
	ifstream fin("input.txt");
	int n, m, k;
	fin >> n >> m >> k;

	vector<vector<pair<int, int>>> adjacList1(n);
	vector<vector<pair<int, int>>> adjacList2(n);
	vector<vector<int>> cichan;

	for (int i = 0; i < m; i++) {
		int v, u, w;
		fin >> v >> u >> w;

		cichan.push_back({ v, u, w });

		adjacList1[v - 1].push_back({ u, w });
		adjacList2[u - 1].push_back({ v, w });
	}

	vector<bool> processed(n);

	vector<lli> distFrom0(n, LLONG_MAX);
	distFrom0[0] = 0;

	priority_queue<pli, vector<pli>, greater<pli>> vertexes;
	vertexes.push({ distFrom0[0], 1 });

	while (!vertexes.empty()) {
		pli vertex = vertexes.top();
		vertexes.pop();
		int v = vertex.second - 1;
		lli dv = vertex.first;
		if (processed[v]) {
			continue;
		}

		processed[v] = true;
		distFrom0[v] = dv;

		for (const pli& uertex : adjacList1[v]) {
			int u = uertex.first - 1;
			lli du = dv + uertex.second;
			if (!processed[u] && du < distFrom0[u]) {
				vertexes.push({ du, u + 1 });
			}
		}
	}

	for (int i = 0; i < processed.size(); i++) {
		processed[i] = false;
	}

	vector<lli> distFromN(n, LLONG_MAX);
	distFromN[n - 1] = 0;

	vertexes.push({distFromN[n - 1], n});

	while (!vertexes.empty()) {
		pli vertex = vertexes.top();
		vertexes.pop();
		int v = vertex.second - 1;
		lli dv = vertex.first;
		if (processed[v]) {
			continue;
		}

		processed[v] = true;
		distFromN[v] = dv;

		for (const pli& uertex : adjacList2[v]) {
			int u = uertex.first - 1;
			lli du = dv + uertex.second;
			if (!processed[u] && du < distFromN[u]) {
				vertexes.push({ du, u + 1 });
			}
		}
	}

	vector<int> answer;

	for (int i = 0; i < cichan.size(); i++) {
		int v = cichan[i][0] - 1;
		int u = cichan[i][1] - 1;
		int w = cichan[i][2];

		//вершины v и u вообще достижимы из 1 и N
		if (distFrom0[v] < LLONG_MAX && distFromN[u] < LLONG_MAX && distFrom0[v] + w + distFromN[u] - distFrom0[n - 1] <= k)
			answer.push_back(i + 1);
	}

	ofstream fout("output.txt");

	fout << answer.size() << '\n';
	for (int i = 0; i < answer.size(); i++) {
		fout << answer[i] << '\n';
	}
	
	return 0;
}
