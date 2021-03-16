import numpy as np
import argparse
def create_parser():

    parser = argparse.ArgumentParser()
    parser.add_argument("--entity_embs", help=" ", default="entity_embedding.npy")
    parser.add_argument("--relation_embs", help=" ", default="relation_embedding.npy")
    return parser

parser = create_parser()
args = parser.parse_args()

entity_data = np.load(args.entity_embs)
np.savetxt('entity2vec.txt', entity_data, delimiter='\t')
relation_data = np.load(args.relation_embs)
np.savetxt('relation2vec.txt', relation_data, delimiter='\t')
